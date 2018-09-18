import boto3
import base64

BUCKET = "3cs-face-recognition"
COLLECTION = "3cs-face-detection"
REGION = "ap-southeast-2"

# Must be in the 3cs-face-recognition bucket to be referenced
EXAMPLE_PHOTO_ID = 'rp_ryan_kurz.jpg'


def get_name_by_image(encoded_image):
    """ Wrapper to make grabbing a name simple
    :param encoded_image: base64 encoded image to search for
    :return:
    """
    faces = search_faces_by_image(encoded_image)
    highest_match = faces[0]
    facial_detail = highest_match['Face']
    matched_image = facial_detail['ExternalImageId']
    stripped_image_name = matched_image.split('.')[0]

    return stripped_image_name


def add_new_face_to_system(photo, first_name, surname, bucket=BUCKET):
    """
    :param photo: base64 encoded image
    :param first_name: first_name of person to be added
    :param surname: surname of person to be added
    :param bucket: s3 bucket to add them to
    :return: the face added to the photo
    """
    photo_name = add_face_to_s3_bucket(photo, first_name, surname, bucket)
    return add_face_to_collection(COLLECTION, BUCKET, photo_name, REGION)


def add_face_to_s3_bucket(photo, first_name, surname, bucket):
    photo = open('test.jpg', 'rb')  # todo replace with actual image from base64

    photo_name = first_name + "_" + surname + '.jpg'

    s3 = boto3.resource('s3')
    s3.Bucket(bucket).put_object(Key=photo_name, Body=photo)
    return photo_name


def search_faces_by_image(encoded_image, collection_id=COLLECTION, threshold=80, region=REGION):
    """

    :param encoded_image: base64 encoded image to search collection for
    :param collection_id: collection_id to add faces to
    :param threshold: threshold that face matching confidence must be above
    :param region: which rekognition region to access
    :return: List of faces and their details that are above specified threshhold
    """
    with open("test.jpg", "rb") as image_file:  # todo remove these lines are for testing
            image_bytes = image_file.read()     # todo remove these lines are for testing

#                image_bytes = base64.decodestring(b64encodedImagehere)
#                todo uncomment the above line is how we will read the base64 encoded image for actual implementation

            rekognition = boto3.client("rekognition", region)
            response = rekognition.search_faces_by_image(
                    Image={"Bytes": image_bytes},
                    CollectionId=collection_id,
                    FaceMatchThreshold=threshold,
            )
            return response['FaceMatches']


def list_faces_in_collection(collection_id=COLLECTION, region=REGION):
    """
    :param collection_id: collection_id to add faces to
    :param region: which rekognition region to access
    :return: List of Face objects
    """

    client = boto3.client('rekognition', region)
    response = client.list_faces(CollectionId=collection_id)

    return response['Faces']


def add_face_to_collection(collection_id=COLLECTION, bucket=BUCKET, photo=EXAMPLE_PHOTO_ID, region=REGION):
    """
    :param collection_id: collection_id to add faces to
    :param bucket: S3 bucket to reference images from
    :param photo: photo name from the bucket specified
    :param region: which rekognition region to access
    :return: list of faces detection in the photo, and added to the collection
    """
    client = boto3.client('rekognition', region)
    response = client.index_faces(CollectionId=collection_id,
                                  Image={'S3Object': {'Bucket': bucket, 'Name': photo}},
                                  ExternalImageId=photo,
                                  DetectionAttributes=['ALL'])

    return response['FaceRecords']


def delete_face_from_collection(faces, collection_id=COLLECTION, region=REGION):
    """
    :param collection_id: collection_id to remove faces from
    :param faces: array of face_ids to remove from specified collection
    :param region: which rekognition region to access
    :return: Array of face_ids that were removed from the collection specified
    """

    client = boto3.client('rekognition', region)
    response = client.delete_faces(CollectionId=collection_id,
                                   FaceIds=faces)

    return response['DeletedFaces']


if __name__ == "__main__":
    add_new_face_to_system(None, "Evan", "Hughes")
