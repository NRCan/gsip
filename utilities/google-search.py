#!/usr/bin/env python
# -*- coding: utf-8 -*-

from googlesearch import search

query = "https://geoconnex.ca/gsip/info/LOD_Node/CAN_Hydro_LOD_Node"

for j in search(query, tld="co.in", num=10, stop=1, pause=2):
  print(j)
