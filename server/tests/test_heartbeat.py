import requests


def test_get_parents():
  r = requests.get('https://deco3801.wisebaldone.com/api/heartbeat')
  assert r.status_code == 200