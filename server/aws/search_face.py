import boto3

BUCKET = "3cs-face-recognition"
COLLECTION = "3cs-face-detection"

import base64


def search_faces_by_image(encoded_image, collection_id, threshold=80, region="ap-southeast-2"):
        with open("test.jpg", "rb") as image_file: ## these lines are for testing
                image_bytes = image_file.read()    ## these lines are for testing

                #image_bytes = base64.decodestring(b64encodedImagehere)
                #the above line is how we will read the base64 encoded image for actual implementation
                
                rekognition = boto3.client("rekognition", region)
                response = rekognition.search_faces_by_image(
                        Image={"Bytes": image_bytes},
                        CollectionId=collection_id,
                        FaceMatchThreshold=threshold,
                )
                return response['FaceMatches']


for record in search_faces_by_image(None, COLLECTION):
	face = record['Face']
	print("Matched Face ({}%)".format(record['Similarity']))
	print("  FaceId : {}".format(face['FaceId']))
	print("  ImageId : {}".format(face['ExternalImageId']))


"""
	Expected output:
	Matched Face (96.6647949219%)
	  FaceId : dc090f86-48a4-5f09-905f-44e97fb1d455
	  ImageId : test.jpg
"""
