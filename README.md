RHYTHMHIVE

The goal of this project is to create an application that will automatically generate a playlist based on the extracted facial expressions of the user to reduce both his time and effort. In order to provide maximum ease to the user voice recognition feature is also included. A user can quickly switch between songs using commands like “Next”, “Previous” and much more using other integrated voice commands. Moving a step forward and making this application the “ultimate” package for music lovers, further special features are also included, such as sharing a song, liking a song etc. A web based app along with its android app is expected to be completed by the end of this project.
The objectives of this project can be split into the following categories:

•	Emotional Recognition:
The main focus of this module is to implement techniques to extract features and classify user’s emotional state. This is possible by capturing images of the users and turning them to greyscale and resizing them. With the help of our own trained neural network (CNN), the final emotional state of the user is returned.

•	 Voice Recognition:
To make this application more captivating and engaging to the audience, this module will allow the users to play with the provided media player by giving it commands like “Pause” , “Stop”, “Next” etc.  

•	 Music Analysis:
After successfully predicting the emotional state of the user (with the help of an API) songs are automatically fetched matching with the user’s current mood. All the information needed is already given in the Meta data of .mp3 file.

High-level system components

The higher level system components of this proposed project are stated below:

•	Frame Capturing:

  o	Real time image processing.

  o	Conversion of frames into grayscale images of 48x48 dimensions.


•	Emotion Detector:

  o	Creation and training of Convolution Neural Network.

  o	Detection of emotion based upon the Neural Network verdict.

  o	Re-detection of mood after end of a song.


•	Music Generation:

  o	Fetch list of songs based on user’s emotion using the metadata of music file.


•	Voice Recognition:

  o	Operate the media player using pre-defined voice commands.


•	Profile Creation and Management:

  o	Sign up & Facebook Login.
  
  o	Follow other people.
  
  o	Search songs (keyword based). 
  
  o	Create and update playlists.
  
  o	Share songs on social networking websites.


•	Data Storage:
  
  o	Storing data of user.

  o	Manipulation of data.

