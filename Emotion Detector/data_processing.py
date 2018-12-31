import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

label_map = ['Anger', 'Disgust', 'Fear', 'Happy', 'Sad', 'Surprise', 'Neutral']

def getData(balanced_ones=True):
    
    Y = []
    X = []
    first = True
    for line in open('data/fer2013.csv'):
        if first:
            first = False
        else:
            row = line.split(',')
            Y.append(int(row[0]))
            X.append([int(p) for p in row[1].split()])
            
    X, Y = np.array(X) / 255.0, np.array(Y)
    
    if balanced_ones:
        X0, Y0 = X[Y!=1, :], Y[Y!=1]
        X1 = X[Y==1, :]
        X1 = np.repeat(X1, 9, axis=0)
        X = np.vstack([X0, X1])
        Y = np.concatenate((Y0, [1]*len(X1)))
        
    return X, Y

def main():
    X, Y = getData(balanced_ones=False)
    c = 1
    path = ""
    for i in range(7):
        x, y = X[Y==i], Y[Y==i]
        N = len(y)
        for j in range(N):
            plt.imshow(x[j].reshape(48,48), cmap='gray')
            plt.title(label_map[y[j]])
            if label_map[y[j]] == 'Anger':
                path = "./root/anger/" + str(c) + ".jpg"
            elif label_map[y[j]] == 'Disgust':
                path = "./root/disgust/" + str(c) + ".jpg"
            elif label_map[y[j]] == 'Fear':
                path = "./root/fear/" + str(c) + ".jpg" 
            elif label_map[y[j]] == 'Happy':
                path = "./root/happy/" + str(c) + ".jpg"
            elif label_map[y[j]] == 'Sad':
                path = "./root/sad/" + str(c) + ".jpg"
            elif label_map[y[j]] == 'Surprise':
                path = "./root/surprise/" + str(c) + ".jpg"
            else:
                path = "./root/neutral/" + str(c) + ".jpg"
            plt.imsave(path, x[j].reshape(48,48), cmap='gray')
            c+=1
       
        
if __name__ == '__main__':
    main()
    
    
    
    