<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Weather App</title>
	
    <!-- Weather Icons CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/weather-icons/2.0.12/css/weather-icons.min.css" />
    
<!--     <link rel="stylesheet" href="weather-icons.min.css" /> -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" />
    <link rel="stylesheet" href="style.css" />

</head>
<body>

    <div class="mainContainer">
     <form action="WeatherServlet" method="post" class="location">
            <input type="text" placeholder="Enter City Name" id="location" value="${city}" name="location"/>
            <button id="searchButton"><i class="fa-solid fa-magnifying-glass"></i></button>
      </form>

        <div class="weatherDetails">
            <div class="weatherIcon">
            	<i class="wi ${weatherIcon} weather-icon"></i>
                <!-- <img src="" alt="${weatherCondition}" id="weather-icon"> -->
                <h2>${temperature} °C</h2>
                 <input type="hidden" id="wc" value="${weatherCondition}" />
                 <input type="hidden" id="wic" value="${weatherIcon}" />
            </div>
            
            <div class="cityDetails">        
                <div class="desc"><strong>${city}</strong></div>
                <div class="date">${date}</div>
            </div>
            <div class="windDetails">
            	<div class="humidityBox">
            	<img src="https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEhgr7XehXJkOPXbZr8xL42sZEFYlS-1fQcvUMsS2HrrV8pcj3GDFaYmYmeb3vXfMrjGXpViEDVfvLcqI7pJ03pKb_9ldQm-Cj9SlGW2Op8rxArgIhlD6oSLGQQKH9IqH1urPpQ4EAMCs3KOwbzLu57FDKv01PioBJBdR6pqlaxZTJr3HwxOUlFhC9EFyw/s320/thermometer.png" alt="Humidity">
                <div class="humidity">
                   <span>Humidity </span>
                   <h2>${humidity}% </h2>
                </div>
               </div> 
               
                <div class="windSpeed">
                    <img src="https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEiyaIguDPkbBMnUDQkGp3wLRj_kvd_GIQ4RHQar7a32mUGtwg3wHLIe0ejKqryX8dnJu-gqU6CBnDo47O7BlzCMCwRbB7u0Pj0CbtGwtyhd8Y8cgEMaSuZKrw5-62etXwo7UoY509umLmndsRmEqqO0FKocqTqjzHvJFC2AEEYjUax9tc1JMWxIWAQR4g/s320/wind.png">
                    <div class="wind">
                    <span>Wind Speed</span>
                    <h2> ${windSpeed} KM/h</h2>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
	<script type="text/javascript" src="weather.js"></script>
	  
</body>
</html>