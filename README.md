<img src="Stage 0 images/Growing logo.png" width="350" height="100">

## PHASE 0
### Team Members

 |    Name and Surname      |             Email              |  Git account  |
 |--------------------------|--------------------------------|---------------|
 | Jorge Adame Prudencio    | j.adame.2018@alumnos.urjc.es   |    jorge-ap   |
 | Antonio Agudo Esperanza  | a.agudo.2018@alumnos.urjc.es   |    agudroid   |
 | Marcos Robles Rodríguez  | m.roblesr.2018@alumnos.urjc.es |    mrcsrobles |
 | Flavia Vásquez Gutiérrez | f.vasquez.2018@alumnos.urjc.es |    flavia29   |

### Team Coordination Tools
*Trello:* [DAW Equipo 3](https://trello.com/b/97ygOHja/daw-equipo3)
___
Welcome to ***Growing***! In this application you can improve yourself by making good activities for your mental and physical health.

## Entities

  * **Users:** Users are the main entity of the application. They will be characterized by the following data:
    - User name.
    - Email. **(Primary key)**
    - Password.
    - Profile photo.
    - Plans completed with *like*.

 Users will have one or more favorite categories, which will be reflected in an N:M relationship, since a user can have several favorite categories and a category can be the favorite of more than one user.

  * **Tree:** Each user will have a tree for each *Category* whose mission will be to make them grow. The data for each tree are:

    - User.
    - Category.
    - Photo.
    - Height.
    - Last Update

     **(Primary Key)**: User and Category.

  * **Category:** This entity has the organise funtion, separating all the web plans by topic, allowing users to distinguish them:

    - Name. **(Primary Key)**
    - Photo.
    - Description.

As a result from the fact that an instance of the Plan entity can be associated at most with only one Category, but a Category can be related to many Plan instances, it will appear as *foreign key* in the Plan's table.

  * **Plan:** In order for users to grow the categories's tree, they will have to perform a series of activities that will be appearing when the *Administrator user* create them. The plans will have associated:
    - Name.
    - Photo.
    - Description.
    - Difficulty.
    - Category.

Because of users can have some favorite plans expressed with *like*, the relationship between this entities will be reflected in an N:M relationship, since a User can have several favorite plans and a Plan can be the favorite of more than one user.

## Users permission

The application has three types of users, which are:

  * **Anonymous user:** Users who are not registered will only be able to view the available categories and the plans they contain.

  * **Registered user:** The registered user, like the *anonymous user*, are able to view the categories and the plans that contains them. In addition, they can edit their profile data except email, which will be unchangeable once the user register it; mark a plan as completed, view their profile information both on the web and in PDF format if they want to, choose favorite category(ies) and observe the progress for each category, represented in multiple charts which will be explained in the section ***Charts***. Finally, they will receive an email when a tree in a category achieve a specific height.

  * **Administrator user:** This user is allowed, in addition to the functionalities described for the previous type of user, to control the record of plans made by users marking them as incompleted, and adding / editing categories and plans.

## Images
The web application will contain one or more images for each entity to increase user satisfaction and making UI more intuitive. These are defined to:

  * **User:** The user will have a profile photo / avatar that can be customized by selecting a file from their own device.

  * **Tree:** The application will show registered users a photo of the status of their tree, which will change its appearance.

  * **Plan:** Each plan will have an icon / image that illustrates the category to which it belongs, another to show the difficulty of completing it, and finally an illustrative photo.

  * **Category:** Each category is defined by an icon / image that can be chosen only by the *Administrator users* in order to graphically show the user the purpose of each one.

## Charts
The application will have four charts. These will only be visible to *Registered users* and *Administrator users*.


  - The first will show the number of *likes* for each category. This information will be displayed in a *Doughnut chart*.

    <img src="Stage 0 images/Doughnut Chart.png" width="300" height="230">

  - The second will illustrate through a *Bar chart* the height of the tree in relation to its category.

    <img src="Stage 0 images/Bar Chart.png" width="700" height="500">

  - The third one will show the number of completed tasks per category for each user, revealed through a *Radar chart*.

    <img src="Stage 0 images/Radar Chart.png" width="350" height="300">


 ## Complementary Technologies
Complementary technologies to be used are:

  * **Sending emails to users:** Registered users will receive an email when they register in the application to confirm said email and also they will receive an email when a tree in a category achieve a specific height.

  * **PDFs Generation:** The user may require the system to view and save all their profile information and progress in the application in PDF format. **(Optional)**

 ## Algorithm or advanced query
  - An algorithm will be used to calculate the height of the tree, which it increases in relation to the plan's completion. Their new height will be defined by calculating its increase proportionally to the difficulty of the task and decreasing when the plans are uncompleted.

  - An algorithm will be used to recommend plans to the user based on their data such as their *likes* and their favorite category.

## PHASE 1
## Screenshots
### Principal page
This screen is the first one that all users see when they access the webapp. In this one, they can see basic information about the initiative, some info about the team like our values and finally some preview of the rest of funtionalities like the categories or a portfolio where they can see a snapshot of every screen.
 <img src="Stage 1 images/indexScreen.png">

### Categories
Because of the main purpose of the webapp is encourage users to make activities, for the UX is a good practice separate them into categories defined by their finality. This ends in a main screen where all the web visitors can differentiate the principal classes. The main interaction lies in some boxes where each one has a title, a short description and finally a *Read More* button, arriving in the next screen explained. Finally, the admin will have visible a button where it can create new ones.

 <img src="Stage 1 images/categoriesScreen.png">

 ### Category information
 This screen represent the information that all webapp categories have. Everyone has the title, on the left side an image of a tree which represents the actual user improvements inside them. Regarding this image, it is important to explain the fact that the tree will be growing through the advances, having 5 stages, which are the following ones:

 <img src="Stage 1 images/stage 1.png" width="135"> → <img src="Stage 1 images/stage 2.png" width="135"> → <img src="Stage 1 images/stage 3.png" width="135"> → <img src="Stage 1 images/stage 4.png" width="135"> → <img src="Stage 1 images/stage 5.png" width="135">

 On the other side, the information displayed consists in a simple list with all the tasks users can complete. Finally, in this screen, the **admin user** will have multiple visible options that allows them to edit each task and technical features of the category:

 <img src="Stage 1 images/mentalHealthScreen.png">

### Explore
In this screen, all the users can see a random selection of plans that are included on the webpage.

 <img src="Stage 1 images/exploreScreen.png">

### About us
This screen only contains the main information about the team membership. This information is composed by a representative photography, the member's full name and its role inside the project.

 <img src="Stage 1 images/aboutUsScreen.png">

### GetStarted
This screen has the main purpose to create new users or sign in users. When users came to this page, they will se the sign in (first picture), but easily can be transfered into the sign up section by clicking the *Sign Up* button.

 <img src="Stage 1 images/signInScreen.png">
 <img src="Stage 1 images/signUpScreen.png">

### Profile
This screen is only visible to the registered users. Inside, users can see an information card, which has a button for traveling to the edition page, explained on a later snapshot; three charts that represents the progress they made and see by category all the plans they did and *dislike* them.

<img src="Stage 1 images/profileScreen.png">  

### Administrator profile
This screen is almost the same screen than the previous one explained. The main differences are ubicated on the information card with the tag: *Admin account* and a table at the end of the page which allows the user to search the record of plans made searching for users and category.

<img src="Stage 1 images/adminProfileScreen.png">

### Edit profile
This page can only be accessed from the registered user / admin profile screen and is composed by a form with some inputs to change the personal information of the actual user.

<img src="Stage 1 images/editProfileScreen.png">


## Navigation diagram
Finally, in the following diagram, you can see all the navigation map inside the webapp:

<img src="Stage 1 images/navigationDiagram.png">

## PHASE 2

### Executing instructions
- Requisites
 * Git
 * Maven
 * MySQL 8.0.23
 * Java JDK 11

### Steps
1. MySQL

 - 1. Initialize MySQL
 - 2. CREATE USER IF NOT EXISTS 'growingUser'@'localhost' IDENTIFIED WITH mysql_native_password BY 'dawEquipo3-2021';
       GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, FILE, INDEX, ALTER, CREATE TEMPORARY TABLES, CREATE VIEW, EVENT, TRIGGER, SHOW VIEW, CREATE ROUTINE, ALTER ROUTINE, EXECUTE, REFERENCES ON *.* TO 'growingUser'@'localhost';
2. CLI

 - 1.	git clone https://github.com/CodeURJC-DAW-2020-21/webapp3.git
 - 2.	cd backend/
 - 3.	mvn org.springframework.boot:spring-boot-maven-plugin:run

3. Browser

 - 1. Go to *https://localhost:8443*
 
### Participation of each member
 
**Jorge Adame Prudencio**
 #### Description
- Mustache application in most of the screens
- Chart adaptation to dynamic values
- Edition of profile, category and plan data
- Like functionality of plans and category (includes some queries to achieve it and modifying the chart)
- Complete task functionality and adapting the correspondent chart
- Email sending configuration
- Error screens
- Image display and its modification in each entity that have it (category, plan(only display) and user)

---
 #### Top 5 commits

- https://github.com/CodeURJC-DAW-2020-21/webapp3/commit/c5da51e324ed3abe663a0328aaf469dc8c13c9d7
- https://github.com/CodeURJC-DAW-2020-21/webapp3/commit/de17f8cc32c408b19d2d8574b3450b23afa9480b
- https://github.com/CodeURJC-DAW-2020-21/webapp3/commit/f785b6e1b34f690fe437e6fbe3fcbb74659d1a25#diff-2c43d4aeb1f25e53ee076b2e1299ecb65375904a1c13aed7d8e95cb9c5982e45
- https://github.com/CodeURJC-DAW-2020-21/webapp3/commit/69c5600b3c2280174e25515ac2eec04e686e777e#diff-342d230187298e3d544a13c39767b235c26ab6ab35213260dee9487c3e6ffef7 (the continuation of the previous one)
- https://github.com/CodeURJC-DAW-2020-21/webapp3/commit/794f4723820f51b3a5ef5982f3dc1bd9a629d3af
- https://github.com/CodeURJC-DAW-2020-21/webapp3/commit/1687d92368c5f789f2712d9a6b9a18c1652a6bdd

---
#### Top 5 files

- EditScreen.html
- controller/CategoryController.java
- controller/UserController.java
- dataBaseLoader.java
- controller/PlanLikeController.java

**Antonio Agudo Esperanza**
#### Description
In this Stage, I have been doing the mustache configuration, unit test, Sign up of users and the validations of the forms and the creation of categories and plans:
Finally, I also worked in the merges with the rest of the team and helped in the conflicts and bugs the merges generated.

---
#### Top 5 commits
-https://github.com/CodeURJC-DAW-2020-21/webapp3/commit/c0a9cb3f19103523f4a42bb14134bd16a49bf3a3
-https://github.com/CodeURJC-DAW-2020-21/webapp3/commit/fa65d283c77cf3fa4dd98032b84ff70ca01ea94f
-https://github.com/CodeURJC-DAW-2020-21/webapp3/commit/03a40b496ec6bd8e2d5e68e5dbd14c859670090c
-https://github.com/CodeURJC-DAW-2020-21/webapp3/commit/c8b879a8866e231362b96def99f2348a72d1d167
-https://github.com/CodeURJC-DAW-2020-21/webapp3/commit/ba988652ce750b10a962b632d52462ec0947838a

---
#### Top 5 files 

-UserController.java
-GrowingController.java
-accordionCategories.html
-GrowingApplicationTests.java
-getStarted.html

---
**Marcos Robles Rodríguez**
 #### Description
In this Stage I have been the responsible of most of the database related tasks, this are:

- Desing of the database
- Modeling of the database
- Development of the database
- Development of needed methods for other functions

I have been also the responsible of the algorithms, therefore, I designed and programmed them.

I also changed the templates and the JS code when needed.

Finally, I also worked in the merges with the rest of the team and helped in the conflicts and bugs the merges generated.

---
 #### Top 5 commits

- https://github.com/CodeURJC-DAW-2020-21/webapp3/commit/2d1e1c158c7b814daf8fa67a74fb6ad6e7cd206b
- https://github.com/CodeURJC-DAW-2020-21/webapp3/commit/fa0080491f453d966673ca0447faf363b2deffe8
- https://github.com/CodeURJC-DAW-2020-21/webapp3/commit/44485090f4a0ad7f9b6d80d2c85c9893f76ee7c8
- https://github.com/CodeURJC-DAW-2020-21/webapp3/commit/f610f9b37c7ed2f29fde61049d6e26a680cdfb6c
- https://github.com/CodeURJC-DAW-2020-21/webapp3/commit/650f7844b2169d6394b4b1f3649969e4ee647d6b

---
#### Top 5 files
1. Growing/backend/src/main/java/es/dawequipo3/growing/repository/Completed_planReposiory.java
2. Growing/backend/src/main/java/es/dawequipo3/growing/model/Completed_plan.java
3. Growing/backend/src/main/java/es/dawequipo3/growing/model/Plan.java
4. Growing/backend/src/main/java/es/dawequipo3/growing/model/Tree.java
5. Growing/backend/src/main/java/es/dawequipo3/growing/model/User.java

--
### Diagrams
- ### Entity Relationship Diagram
 <img src="Stage 2 images/relationDB.svg" height="800px">
 
- ### Classes and Templates Diagram
<img src="Stage 2 images/classAndTemplatesDiagram.svg">

***Note:*** For a better visualization, the image is an SVG file.
 - ### Navigation Diagram
 <img src="Stage 2 images/NavigationDiagramDAW.svg" height="800px">
