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
 
 #### Category information
 This screen represent the information that all webapp categories have. Everyone has the title, on the left side an image of a tree which represents the actual user improvements inside them. Regarding this image, it is important to explain the fact that the tree will be growing through the advances, having 5 stages, which are the following ones:
 
 <img src="Stage 1 images/stage 1.png" width="135"> → <img src="Stage 1 images/stage 2.png" width="135"> → <img src="Stage 1 images/stage 3.png" width="135"> → <img src="Stage 1 images/stage 4.png" width="135"> → <img src="Stage 1 images/stage 5.png" width="135">
 
 On the other side, the information displayed consists in a simple list with all the tasks users can complete. Finally, in this screen, the **admin user** will have multiple visible options that allows them to edit each task and technical features of the category:
 
 <img src="Stage 1 images/mentalHealthScreen.png">
 
### Explore
In this screen, all the users can see a random selection of plans that are included on the webpage.

 <img src="Stage 1 images/explore.png">
 
### About us
This screen only contains the main information about the team membership. This information is composed by a representative photography, the member's full name and its role inside the project.

 <img src="Stage 1 images/aboutUsScreen.png">
 
### GetStarted
This screen has the main purpose to create new users or sign in users. When users came to this page, they will se the sign in (first picture), but easily can be transfered into the sign up section by clicking the *Sign Up* button.

 <img src="Stage 1 images/loginRegisterSignIn.png">
 <img src="Stage 1 images/loginRegisterSignUp.png">

### Profile
This screen is only visible to the registered users. Inside, users can see an information card, which has a button for traveling to the edition page, explained on a later snapshot; three charts that represents the progress they made and see by category all the plans they did and *dislike* them.

<img src="Stage 1 images/profileScreen.png">  

### Administrator profile
This screen is almost the same screen than the previous one explained. The main differences are ubicated on the information card with the tag: *Admin account* and a table at the end of the page which allows the user to search the record of plans made searching for users and category.

<img src="Stage 1 images/adminProfileScreen.png">

#### Edit profile
This page can only be accessed from the registered user / admin profile screen and is composed by a form with some inputs to change the personal information of the actual user.

<img src="Stage 1 images/editProfileScreen.png">


## Navigation diagram
Finally, in the following diagram, you can see all the navigation map inside the webapp:

<img src="Stage 1 images/navigationDiagram.png">
