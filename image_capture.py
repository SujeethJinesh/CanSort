import picamera
from clarifai.client import ClarifaiApi

clarifai_api = ClarifaiApi() #set up clarifai api

#setup up cam to close when finished
with picamera.PiCamera() as camera:
    camera.resolution = (1280, 720) #may want to change this
                                    #depending on speeds requirements
    camera.capture("/home/pi/Desktop/CanSort/capturedImage.jpg")
    result = clarifa_api.tag_images(open("/home/pi/Desktop/CanSort/capturedImage.jpg"))
