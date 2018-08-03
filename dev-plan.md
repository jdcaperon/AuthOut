Dev Plan
========

## Endpoints

### Beta ( Git:Develop )
- deco3801.wisebaldone.com/jenkins
  - Instance of Jenkins.io for running tests and pushing product to the deployment servers.
- deco3801.wisebaldone.com/sonar
  - Instance of sonarqube code quality analysis tool.
- deco3801.wisebaldone.com/api
  - Instance of the api server beta.
- deco3801.wisebaldone.com/docs
  - the docs beta folder published.
- deco3801.wisebaldone.com/app
  - the beta react based webapp
- deco3801.wisebaldone.com/
  - the beta product website ( Informative product website ). Also react based but purely frontend.

### Production ( Git:Master )
- DECO3801-RocketPotatoes.uqcloud.net/
  - Production version of the product website.
- DECO3801-RocketPotatoes.uqcloud.net/app
  - Production version of the web application.
- DECO3801-RocketPotatoes.uqcloud.net/api
  - Production version of the web server.
- DECO3801-RocketPotatoes.uqcloud.net/docs
  - Production version of the docs website.

## Git Layout

- Master
  - Gets pushed to production if tests pass ( should pass since they passed on develop )
- Develop
  - Gets pushed to beta if tests pass