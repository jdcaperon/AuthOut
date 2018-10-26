import requests


def test_get_parents():
  r = requests.get('https://deco3801.wisebaldone.com/api/parent')
  assert r.status_code == 200
  data = r.json()
  assert len(data) != 0
  for parent in data:
    assert 'id' in parent
    assert 'email' in parent
    assert 'first_name' in parent
    assert 'last_name' in parent
    assert 'date_of_birth' in parent
    assert 'mobile_number' in parent
    assert 'children' in parent
    assert 'trusted_children' in parent

    # get this individual parent through the specified endpoint
    w = requests.get('https://deco3801.wisebaldone.com/api/parent/{}'.format(parent['id']))
    assert w.status_code == 200
    assert w.json()['id'] == parent['id']

    x = requests.get('https://deco3801.wisebaldone.com/api/parent/{}/children'.format(parent['id']))
    assert x.status_code == 200
    print(x.json()['children'])
    assert len(x.json()['children']) == len(parent['children'])

    z = requests.get('https://deco3801.wisebaldone.com/api/parent/{}/children/trusted'.format(parent['id']))
    assert z.status_code == 200
    print(z.json()['children'])
    assert len(z.json()['children']) == len(parent['trusted_children'])

def test_post_invalid_parent():
  # The data field does not contain all the information nesscary.
  data = {'email': 'support@nowhere.com'}
  r = requests.post('https://deco3801.wisebaldone.com/api/parent/', json=data)
  assert r.status_code == 400