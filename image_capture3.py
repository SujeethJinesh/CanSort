import picamera
from clarifai import rest
from clarifai.rest import ClarifaiApp
from clarifai.rest import Image as ClImage
import json

clarifai_api = ClarifaiApp('G0-QUxVEAKj9o6u13ZhU12AGFhlEZlGFOVVllNHz',
                           'hHh5e_q2ePkBmtJ1bD21L61KXAjZc2Ulf8DbKKhb') #set up clarifai api

recyclables = {"bottle", "plastic"}
compost = {"apple", "clementine", "orange", "fruit", "tangerine", "mandarin", "food"}
landfill = {"phone", "wrapper"}

#setup up cam to close when finished
with picamera.PiCamera() as camera:
    camera.resolution = (150, 150) #may want to change this
                                    #depending on speeds requirements
    camera.capture("/home/pi/Desktop/CanSort/capturedImage.jpg")
    
model = clarifai_api.models.get('general-v1.3')
data = model.predict_by_filename(filename="/home/pi/Desktop/CanSort/capturedImage.jpg")

found = False
for concept in data["outputs"][0]["data"]["concepts"]:
    name = concept["name"]
    print(name)
    if (name in recyclables):
        found = True
        print("Recycle that shit!")
        break
    elif (name in compost):
        found = True
        print("Compost that shit!")
        break
    elif (name in landfill):
        found = True
        print("Trash that shit!")
        break
    
if (not found):
    print("Trash that shit!")
