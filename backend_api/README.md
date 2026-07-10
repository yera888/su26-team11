# Swap-A-Bookaroo Backend API

**Version:** 1.0  
**Last Updated:** July 10, 2026

**Base URL:**

- local: http://localhost:8080
- production:

## Table of Contents

1. [Overview](#1-overview)
2. [UML Class Diagram](#2-uml-class-diagram)
3. [API Endpoints](#3-api-endpoints)
   - [Customer Endpoints](#31-customer-endpoints)
   - [Provider Endpoints](#32-provider-endpoints)
   - [Book Listing Endpoints](#33-book-listing-endpoints)
   - [Tag Endpoints](#34-tag-endpoints)
   - [Swap Request Endpoints](#35-swap-request-endpoints)
   - [Swap History Endpoints](#36-swap-history-endpoints)
4. [Use Case Mapping](#4-use-case-mapping)

---

## 1. Overview

The Swap-A-Bookaroo backend exposes a RESTful API for a genre-based book-swapping platform. The system allows customers to find books based on genre preferences and allows providers to list books they want to swap with other users.

This backend supports provider profile creation, book listing creation, listing management, swap request tracking, and provider swap history. Providers can create an account, manage their provider profile, post books using title, author, ISBN, genre tags, and an uploaded image or image path, and view requests from customers who are interested in swapping.

Customer-side API details are intentionally left blank for the customer-side developer to complete.

---

## 2. UML Class Diagram

![UML Class Diagram](../docs/uml-class-diagram.png)

---

## 3. API Endpoints

### 3.1 Customer Endpoints

Customer-side endpoints will be completed by the customer-side developer.

#### Create a customer

```http
POST /api/customers