server {
  server_name deco3801.wisebaldone.com;

  listen [::]:443 ssl ipv6only=on;
  listen 443 ssl;

  ssl_certificate /etc/letsencrypt/live/deco3801.wisebaldone.com/fullchain.pem;
  ssl_certificate_key /etc/letsencrypt/live/deco3801.wisebaldone.com/privkey.pem;
  include /etc/letsencrypt/options-ssl-nginx.conf;
  ssl_dhparam /etc/letsencrypt/ssl-dhparams.pem;
  
  
  location /api {
    # this will be changed to a proxy
    root /var/www/deco3801.wisebaldone.com/api;
  }

  location /docs {
    root /var/www/deco3801.wisebaldone.com;
  }

  location / {
    root /var/www/deco3801.wisebaldone.com/html;
  }
}

server {
  if ($host = deco3801.wisebaldone.com) {
    return 301 https://$host$request_uri;
  }

  server_name deco3801.wisebaldone.com;
  listen 80;
  listen [::]:80;

  return 404;
}