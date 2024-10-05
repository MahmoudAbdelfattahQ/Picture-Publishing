# Picture Publishing Service

## Overview

The Picture Publishing Service is a web application where registered and logged-in users can upload pictures for acceptance or rejection. An administrator can log in to review all submissions. Accepted pictures will be assigned a URL accessible to all users (without the need to log in), while rejected pictures will have their files removed but retain their metadata.

## Detailed Requirements

### User Registration
- **End-users can register** with the following information:
  - Email address
  - Password

### Landing Page
- Displays all accepted pictures' URLs.
- Non-logged-in users can click on the links to view the pictures.

### Picture Upload
- Logged-in users can upload a picture along with the following fields:
  - **Description**
  - **Category** (three fixed categories):
    - Living Thing
    - Machine
    - Nature
  - **Attachment** (up to 2 MB, file types: jpg, png, gif only)

### Administrative Features
- An administrative user can log in to a separate admin page.
  - Built-in admin credentials:
    - Username: `admin`
    - Password: `admin123`
- The admin can view a list of uploaded pictures that are unprocessed (accepted or rejected pictures do not appear here).
- Admin can select a picture to process, which opens a new page displaying:
  - Description
  - Category
  - Picture dimensions
  - The picture itself
- The admin can choose to:
  - **Accept** the picture, generating a URL that will be displayed on the Landing page.
  - **Reject** the picture, which will remove the picture file from storage while marking the data record as rejected.

## Nonfunctional Requirements
- The backend database should be an open-source standard SQL variety, specifically **PostgreSQL**.
- Must provide scripts for deployment and running unit tests.

## Expected Deliverables
- The entire project: code and database.
- REST API documentation (Swagger preferred, but any clear documentation is acceptable).

## Installation

### Prerequisites
- Java 11 or higher
- Maven
- PostgreSQL

### Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/MahmoudAbdelfattahQ/picture-publishing-service.git
   cd picture-publishing-service
