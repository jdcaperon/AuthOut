import boto3

if __name__ == "__main__":

    collectionId='3cs-face-detection'
    faces=[]
    faces.append("b3e82841-9069-4087-94bf-4207b55a75cb")

    client=boto3.client('rekognition','ap-southeast-2')

    response=client.delete_faces(CollectionId=collectionId,
                               FaceIds=faces)
    
    print(str(len(response['DeletedFaces'])) + ' faces deleted:') 							
    for faceId in response['DeletedFaces']:
         print (faceId)
