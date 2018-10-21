import boto3
import base64

BUCKET = "3cs-face-recognition"
COLLECTION = "3cs-face-detection"
REGION = "ap-southeast-2"
OBJECT_PARENT = "p"


def get_id_by_image(encoded_image):
    """ Wrapper to make grabbing a name simple
    :param encoded_image: base64 encoded image to search for
    :return:
    """
    faces = search_faces_by_image(encoded_image)
    if len(faces) == 0:
        return None
    highest_match = faces[0]
    facial_detail = highest_match['Face']
    matched_image = facial_detail['ExternalImageId']
    stripped_image_name = matched_image.split('.')[0]
    object_id = stripped_image_name.split('_')[1]
    return object_id


def set_parent_photo(parental_id, photo, bucket=BUCKET):
    """
    :param photo: base64 encoded image
    :param parental_id: ID of the parent to which this face is being applied
    :param bucket: s3 bucket to add them to
    :return: the face added to the photo
    """
    photo_name = add_photo_to_s3_bucket(photo, parental_id, OBJECT_PARENT, bucket)
    return add_face_to_collection(COLLECTION, BUCKET, photo_name, REGION)


def add_photo_to_s3_bucket(photo, object_id, object_type, bucket=BUCKET):
    """
    :param photo: base64 encoded image
    :param object_id: Parental/Child id of person that owns this image
    :param object_type: String which defines the object type
    :param bucket: s3 bucket to add them to
    :return: the name of the image saved
    """
    photo_name = calc_photo_name(object_id, object_type)

    s3 = boto3.resource('s3')
    s3.Bucket(bucket).put_object(Key=photo_name, Body=photo)
    return photo_name


def get_photo_from_s3_bucket(object_id, object_type, bucket=BUCKET):
    photo_name = calc_photo_name(object_id, object_type)

    s3 = boto3.client('s3')
    data = b""
    try:
        data = base64.b64encode(s3.get_object(Bucket=bucket, Key=photo_name)['Body'].read())
    except Exception as e:
        # do nothing
        data = b""
    return data


def get_parent_photo(parental_id):
    return get_photo_from_s3_bucket(parental_id, OBJECT_PARENT)


def delete_photo_from_s3_bucket(keys, bucket=BUCKET):
    """
    :param keys: list of image names to delete
    :param bucket: s3 bucket to remove them from
    :return: None
    """
    keys_to_delete = []
    for key in keys:
        keys_to_delete.append({'Key': key})

    s3 = boto3.resource('s3')
    s3.Bucket(bucket).delete_objects(
        Bucket=bucket,
        Delete={'Objects': keys_to_delete}
    )


def get_face_id_by_photo_name(photo_name, collection_id=COLLECTION, region=REGION):
    faces = list_faces_in_collection(collection_id, region)
    for face in faces:
        if face['ExternalImageId'] == photo_name:
            return face['FaceId']


def delete_parent_photo(parental_id):
    name = calc_photo_name(parental_id, OBJECT_PARENT)
    key = [name]
    delete_photo_from_s3_bucket(key)

    face_id = get_face_id_by_photo_name(name)
    delete_face_from_collection([face_id])


def search_faces_by_image(encoded_image, collection_id=COLLECTION, threshold=60, region=REGION):
    """

    :param encoded_image: base64 encoded image to search collection for
    :param collection_id: collection_id to add faces to
    :param threshold: threshold that face matching confidence must be above
    :param region: which rekognition region to access
    :return: List of faces and their details that are above specified threshhold
    """
    print(encoded_image)
    image_bytes = base64.decodestring(bytes(encoded_image, 'ASCII'))

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


def add_face_to_collection(collection_id=COLLECTION, bucket=BUCKET, photo="__NOT_SET__", region=REGION):
    """
    :param collection_id: collection_id to add faces to
    :param bucket: S3 bucket to reference images from
    :param photo: photo name from the bucket specified
    :param region: which rekognition region to access
    :return: list of faces detection in the photo, and added to the collection
    """
    assert(photo != "__NOT_SET__")
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


def calc_photo_name(object_id, object_type):
    return object_type + '_' + str(object_id) + '.jpg'
