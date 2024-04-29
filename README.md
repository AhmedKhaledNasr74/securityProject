# securityProject

*******************************************************************************************************************************************
First Request Mapping ( "/auth" )

-------------------------------------------------------------------------------------------------------------------------------------------

	1- end point = "/auth/signUp" , 
	   methode   = post ,
           parameter = UserInfo -> example for json {
							 "name"     : "ahmed" ,
							 "password" : (will be encodeed in database)"123" ,
							 "email"    : "ahmed@123",
							 "roles"    : (will be default from data base = "ROLE_USER")
							};
-------------------------------------------------------------------------------------------------------------------------------------------

-------------------------------------------------------------------------------------------------------------------------------------------


        2- end point = "/auth/signIn" ,
	   methode   = post ,
	   paramter  = AuthRequest -> example for json {
							"username" : "ahmed",
							"password" : "123"
							};
	   return = token 
-------------------------------------------------------------------------------------------------------------------------------------------

*****************************************************************************************************************************************************
Second Request Mapping ( "/tasks" ) -> to access any endpoint in theis request mpping you must be authenticated(have valid token)

-------------------------------------------------------------------------------------------------------------------------------------------

	1- endpoint  = "/tasks/add" , access = "ROLE_ADMIN",
           methode   = post , Auth = "Bearer Token"
	   parameter = Task -> example for json {
                                                  "name": "Task 2",
                                                  "deadLine": "2024-05-10",
                                                  "done": (will be default from data base = false)
						  };
-------------------------------------------------------------------------------------------------------------------------------------------
	2- endpoint  = "/tasks/admin/{taskId}" , access = "ROLE_ADMIN" -> will update task
	   methode   = put,
	   parameter = Task (updated data)
-------------------------------------------------------------------------------------------------------------------------------------------
	3- endpoint = "/tasks/admin/{taskId}" , access = ROLE_ADMIN -> will delete task
	   methode  = delete 
-------------------------------------------------------------------------------------------------------------------------------------------
 	4- endpoint = "/tasks/user/markDone/{taskId}" , access = "ROLE_USER" -> will make done equal to true
           methode  = put
-------------------------------------------------------------------------------------------------------------------------------------------
	5- endpoint = "/tasks/admin/{taskId}" , access = "Role_ADMIN" -> will return the task with thes id 
           methode  = get
           return   = will return spacific task
-------------------------------------------------------------------------------------------------------------------------------------------
	6- endpoint = "/tasks/admin/mainPage" , access = "ROLE_ADMIN" -> will get all tasks
           methode  = get
	   return   = all tasks
-------------------------------------------------------------------------------------------------------------------------------------------
	7- endpoint = "/tasks/user/mainPage" , access = "ROLE_USER" -> will get tasks that not finished yet (is_done = false "in database")
	   methode  = get
	   return   = all unfinished tasks
