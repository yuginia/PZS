CREATE TABLE Clients(
    clients_id int GENERATED BY DEFAULT AS IDENTITY 
                                         (START WITH 1, INCREMENT BY 1) NOT NULL,
    name varchar(10),
    user_id int,
    PRIMARY KEY (clients_id)    
);

CREATE TABLE Users(
    users_id int GENERATED BY DEFAULT AS IDENTITY 
                                         (START WITH 1, INCREMENT BY 1) NOT NULL,
    login varchar(10),
    password varchar(64),
    password_text varchar(10),
    PRIMARY KEY (users_id)
);

CREATE TABLE User_Authorization(
    user_authorization_id int GENERATED BY DEFAULT AS IDENTITY 
                                         (START WITH 1, INCREMENT BY 1) NOT NULL,
    user_id int,
    role varchar(50),
    PRIMARY KEY (user_authorization_id)
);