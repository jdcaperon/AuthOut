
import boto3

if __name__ == "__main__":

    bucket='3cs-face-recognition'
    collectionId='3cs-face-detection'
    photo='rp_ryan_kurz.jpg'
    
    client=boto3.client('rekognition','ap-southeast-2')

    response=client.index_faces

    response=client.index_faces(CollectionId=collectionId,
                                Image={'S3Object':{'Bucket':bucket,'Name':photo}},
                                ExternalImageId=photo,
                                DetectionAttributes=['ALL'])

    print ('Faces in ' + photo) 							
    for faceRecord in response['FaceRecords']:
         print (faceRecord['Face']['FaceId'])
