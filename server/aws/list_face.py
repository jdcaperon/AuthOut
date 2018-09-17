import boto3

if __name__ == "__main__":

    bucket='3cs-face-recognition'
    collectionId='3cs-face-detection'
    maxResults=2
    tokens=True

    client=boto3.client('rekognition','ap-southeast-2')
    response=client.list_faces(CollectionId=collectionId,
                               MaxResults=maxResults)

    print('Faces in collection ' + collectionId)

 
    while tokens:

        faces=response['Faces']

        for face in faces:
            print (face)
        if 'NextToken' in response:
            nextToken=response['NextToken']
            response=client.list_faces(CollectionId=collectionId,
                                       NextToken=nextToken,MaxResults=maxResults)
        else:
            tokens=False
