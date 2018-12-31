import torch
import torchvision
import torchvision.transforms as transforms
from torch.autograd import Variable
import os
 
transform = transforms.Compose(
    [transforms.ToTensor(),
     transforms.Normalize((0.5, 0.5, 0.5), (0.5, 0.5, 0.5))])


trainset = torchvision.datasets.ImageFolder(root='./root', transform=transform)
trainloader = torch.utils.data.DataLoader(trainset, batch_size=4,
                                          shuffle=True, num_workers=2)

testset = torchvision.datasets.ImageFolder(root='./faces', transform=transform)
testloader = torch.utils.data.DataLoader(testset, batch_size=1,
                                         shuffle=False, num_workers=2)

classes = ('Anger', 'Disgust', 'Fear', 'Happy', 'Sad', 'Surprise', 'Neutral')

import matplotlib.pyplot as plt
import numpy as np

def imshow(img):
    img = img / 2 + 0.5     # unnormalize
    npimg = img.numpy()
    plt.imshow(np.transpose(npimg, (1, 2, 0)))
    
import torch.nn as nn
import torch.nn.functional as F


class Net(nn.Module):
    def __init__(self):
        super(Net, self).__init__()
        self.conv1 = nn.Conv2d(3, 32, 5)
        self.pool = nn.MaxPool2d(2, 2)
        self.conv2 = nn.Conv2d(32, 64, 5)
        self.fc1 = nn.Linear(64*9*9, 1024)
        self.fc2 = nn.Linear(1024, 512)
        self.fc3 = nn.Linear(512, 7)

    def forward(self, x):
        x = self.pool(F.relu(self.conv1(x)))
        x = self.pool(F.relu(self.conv2(x)))
        x = x.view(x.size(0), -1)
        x = F.relu(self.fc1(x))
        x = F.relu(self.fc2(x))
        x = self.fc3(x)
        return x


net = Net()
save_net = net

import torch.optim as optim

criterion = nn.CrossEntropyLoss()
optimizer = optim.SGD(net.parameters(), lr=0.001, momentum=0.9)
#optimizer = optim.Adam(net.parameters(), lr=0.001)

#for epoch in range(10):  # loop over the dataset multiple times
#
#    running_loss = 0.0
#    for i, data in enumerate(trainloader, 0):
#        # get the inputs
#        real, labels = data
#        
#        inputs = Variable(real)
#        labels = Variable(labels)
#        #labels = Variable(torch.ones(inputs.size()[0]))
#        # zero the parameter gradients
#        optimizer.zero_grad()
#        
#        # forward + backward + optimize
#        outputs = net(inputs)
#        loss = criterion(outputs, labels)
#        loss.backward()
#        optimizer.step()
#        # print statistics
#        running_loss += float(loss.data[0])
#        if i % 100 == 0:    # print every 100 mini-batches
#            print('[%d, %5d] loss: %.3f' %
#                  (epoch + 1, i + 1, running_loss / 100))
#            running_loss = 0.0
#
#print('Finished Training')
#torch.save({'state_dict': save_net.state_dict(),
#                    'optimizer' : optimizer.state_dict(),
#                   }, 'last_brain1.pth')

if os.path.isfile('last_brain1.pth'):
    print("=> loading checkpoint... ")
    checkpoint = torch.load('last_brain1.pth')
    net.load_state_dict(checkpoint['state_dict'])
    optimizer.load_state_dict(checkpoint['optimizer'])
    print("done !")
else:
    print("no checkpoint found...")
    
dataiter = iter(testloader)
images, labels = dataiter.next()

# print images
#imshow(torchvision.utils.make_grid(images))
#print('GroundTruth: ', ' '.join('%5s' % classes[labels[j]] for j in range(4)))

real = Variable(images)
outputs = net(real)


_, predicted = torch.max(outputs, 1)

#print('Predicted: ', ' '.join('%5s' % classes[predicted[j]]
#                              for j in range(4)))


correct = 0
total = 0
with torch.no_grad():
    for data in testloader:
        images, labels = data
        images = Variable(images)
        labels = Variable(labels)
        outputs = net(images)
        _, predicted = torch.max(outputs.data, 1)
        print(predicted)
        total += labels.size(0)
        correct += float((predicted == labels).sum().item())

print('Accuracy of the network: %f %%' % (
    100 * correct / total))

#class_correct = list(0. for i in range(7))
#class_total = list(0. for i in range(7))
#with torch.no_grad():
#    for data in testloader:
#        images, labels = data
#        images = Variable(images)
#        labels = Variable(labels)
#        outputs = net(images)
#        _, predicted = torch.max(outputs, 1)
#        c = (predicted == labels).squeeze()
#        for i in range(4):
#            label = labels[i]
#            class_correct[label] += c[i].item()
#            class_total[label] += 1
#
#
#for i in range(7):
#    print('Accuracy of %5s : %2f %%' % (
#        classes[i], 100 * class_correct[i] / class_total[i]))

