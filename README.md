# DBL4J

## Introduction

> DBL4J is a simple Java API for discordbotslist.co

## Code Samples

    public static void main(String[] args) {

        DiscordBotsListAPI discordBotsListAPI = new DiscordBotsListAPI("Your API key");

        // Prints out the response, and posts the data. You can use the postStats method without any printing.
        System.out.println(discordBotsListAPI.postStats("848238190355283988", 4, 1));

        try {
            // Prints out the information
            System.out.println(discordBotsListAPI.getBotInformation("848238190355283988"));
        } catch (IOException ioException) {
            // Something went wrong!
            ioException.printStackTrace();
        }
        
    }

## Installation

## Maven
	<repository>
	    <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
	</repository>

	<dependency>
	    <groupId>com.github.cybooo</groupId>
	    <artifactId>DBL4J</artifactId>
	    <version>VERSION</version>
	</dependency>
## Gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

	dependencies {
	        implementation 'com.github.cybooo:DBL4J:Tag'
	}
