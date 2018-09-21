---
id: mvp
title: MVP details
sidebar_label: MVP details
---

# Overview

The MVP database and website are both accessible from an external domain located at deco3801.wisebaldone.com, which is where your reading this.

# Hosting Access

The web access to the server is publically available with key locations for diffent content:

* deco3801.wisebaldone.com => product website ( wip )
* deco3801.wisebaldone.com/app => web app endpoint
* deco3801.wisebaldone.com/docs => developer documentation
* deco3801.wisebaldone.com/docs/api => ReST API documentation
* deco3801.wisebaldone.com/api => ReST API endpoing.

Remote access to the server can be done by sshing into the domain with the username: deco3801 and the authentication is the username typed in reverse.

# Database Access

To access the database, once your signed into the server you can access the postgres database by using:

`psql authout`

when your presented with `authout=>` you can type `\l` to see all the databases on the system. Since we connected with the AuthOut database as a command line parameter we can then list the scehma of this database. This is done by first getting the table name by running `\d+` then once a table name is chosen `\d+ children` as an example.