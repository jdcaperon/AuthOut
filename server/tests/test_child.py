import requests


def test_get_children():
  r = requests.get('https://deco3801.wisebaldone.com/api/child')
  assert r.status_code == 200
  data = r.json()
  assert len(data) != 0
  for child in data:
    assert 'id' in child
    assert 'first_name' in child
    assert 'last_name' in child
    assert 'date_of_birth' in child
    assert 'status' in child

    # get this individual child through the specified endpoint
    w = requests.get('https://deco3801.wisebaldone.com/api/child/{}'.format(child['id']))
    assert w.status_code == 200
    assert w.json()['id'] == child['id']

def test_post_invalid_child():
  # The data field does not contain all the information nesscary.
  data = {'first_name': 'hello'}
  r = requests.post('https://deco3801.wisebaldone.com/api/child/', json=data)
  assert r.status_code != 200