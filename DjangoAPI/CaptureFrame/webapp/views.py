from django.shortcuts import render
from rest_framework.views import APIView
from rest_framework.response import Response
import cv2
import numpy as np

class MoodDetector(APIView):
    
    # Load functions
    def face_extractor(img):
        # Function detects faces and returns the cropped face
        # If no face detected, it returns the input image
    
        gray = cv2.cvtColor(img,cv2.COLOR_BGR2GRAY)
        faces = face_classifier.detectMultiScale(gray, 1.3, 5)
    
        if faces is ():
            return None
    
        # Crop all faces found
        for (x,y,w,h) in faces:
            cropped_face = img[y:y+h, x:x+w]

        return cropped_face
    
    def get(self, request):
        
        # Load HAAR face classifier
        face_classifier = cv2.CascadeClassifier('E:\FYP\Emotion Detector\haarcascade_frontalface_default.xml')
        # Initialize Webcam
        cap = cv2.VideoCapture(0)
        count = 0
        
        # Collect 20 samples of your face from webcam input
        while True:

            ret, frame = cap.read()
            if face_extractor(frame) is not None:
                count += 1
                face = cv2.resize(face_extractor(frame), (48, 48))
                face = cv2.cvtColor(face, cv2.COLOR_BGR2GRAY)

            # Save file in specified directory with unique name
            file_name_path = 'E:/FYP/Emotion Detector/faces/anger' + str(count) + '.jpg'
            cv2.imwrite(file_name_path, face)

            # Put count on images and display live count
#           cv2.putText(face, str(count), (50, 50), cv2.FONT_HERSHEY_COMPLEX, 1, (0,255,0), 2)
#           cv2.imshow('Face Cropper', face)
        
        else:
            print("Face not found")
            pass

        if cv2.waitKey(1) == 13 or count == 20: #13 is the Enter Key
            break
        
        cap.release()
        cv2.destroyAllWindows()      
        return Response("Collecting Samples Complete")
    
    
    
    def post(self):
        pass

