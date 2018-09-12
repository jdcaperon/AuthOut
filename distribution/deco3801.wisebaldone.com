server {
  server_name deco3801.wisebaldone.com;

  listen [::]:443 ssl ipv6only=on;
  listen 443 ssl;

  ssl_certificate /etc/letsencrypt/live/deco3801.wisebaldone.com/fullchain.pem;
  ssl_certificate_key /etc/letsencrypt/live/deco3801.wisebaldone.com/privkey.pem;
  include /etc/letsencrypt/options-ssl-nginx.conf;
  ssl_dhparam /etc/letsencrypt/ssl-dhparams.pem;
  
  
  location /api/ {
    proxy_pass http://127.0.0.1:8000/;
  }

  location /docs {
    root /var/www/deco3801.wisebaldone.com;
  }

  location /app {
    root /var/www/deco3801.wisebaldone.com/app;
    try_files $uri $uri/ %uri.html $uri.php =404;
    index index.html index.php

    location ~ \.php$ {
      include snippets/fastcgi-php.conf;
      fastcgi_pass unix:/var/run/php/php7.2-fpm.sock
    }
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