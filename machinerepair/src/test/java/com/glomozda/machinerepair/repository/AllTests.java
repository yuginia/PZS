package com.glomozda.machinerepair.repository;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.glomozda.machinerepair.repository.client.ClientDAOJDBCTest;
import com.glomozda.machinerepair.repository.user.UserDAOJDBCTest;
import com.glomozda.machinerepair.repository.userauthorization.UserAuthorizationDAOJDBCTest;

@SuppressWarnings({"PMD.CommentRequired", "PMD.LawOfDemeter"})
@RunWith(Suite.class)
@SuiteClasses({ ClientDAOJDBCTest.class,
				UserDAOJDBCTest.class,
				UserAuthorizationDAOJDBCTest.class,				
			})

public class AllTests {

}
