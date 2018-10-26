import requests


def test_get_entries():
  r = requests.get('https://deco3801.wisebaldone.com/api/entry')
  assert r.status_code == 200
  data = r.json()
  assert 'signed_in' in data
  assert 'signed_out' in data
  assert 'entries' in data

def test_get_entry_query():
  send = {'id': 0, 'lower': '1/1/2018', 'upper': '2/1/2018'}
  r = requests.post('https://deco3801.wisebaldone.com/api/entry/query', json=send)
  assert r.status_code == 200
  data = r.json()
  assert 'entries' in data

def test_get_entry_stats():
  send = {'lower': '1/1/2018', 'upper': '2/1/2018'}
  r = requests.post('https://deco3801.wisebaldone.com/api/entry/stats', json=send)
  assert r.status_code == 200
  data = r.json()
  assert 'days' in data
  assert len(data['days']) == 3