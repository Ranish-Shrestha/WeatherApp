package WeatherPackage;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class WeatherServlet
 */
@WebServlet("/WeatherServlet")
public class WeatherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WeatherServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// Get the city from input
		String location = request.getParameter("location").replace(" ", "%20");
		
		// OpenWeatherMap API key
		String apiKey = "f98f9cd92021cb9264cb98852991d52d";
		
		// Create URL for OpenWeatherMap API request
		String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q="+ location +"&appid=" + apiKey;
		
		// API integration
		URL url = new URL(apiUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		
		// Fetch data from API
		InputStream inputStream = connection.getInputStream();
		InputStreamReader reader = new InputStreamReader(inputStream);
		
		// Storing data into String
		StringBuilder responseContent = new StringBuilder();
		Scanner sc = new Scanner(reader);
		
		while(sc.hasNext()) {
			responseContent.append(sc.nextLine());
		}
		
		sc.close();
		
		// Check response from API
		System.out.println(responseContent);
		
		// TypeCasting or Parsing data into JSON
		Gson gson = new Gson();
		JsonObject jsonObject = gson.fromJson(responseContent.toString(), JsonObject.class);
		
		// Date and Time
		//long dateTimeStamp = jsonObject.get("dt").getAsLong() * 1000;
		long dt = jsonObject.get("dt").getAsLong();
		int timeZone = jsonObject.get("timezone").getAsInt();
		String date = convertTime(dt, timeZone);
		
		//long dateTimeStamp =  1000 + jsonObject.get("dt").getAsLong() * 1000;
		//String date = new Date(dateTimeStamp).toString();
		//Date date = (new Date((jsonObject.getAsJsonObject("sys").get("sunrise").getAsLong() + jsonObject.get("timezone").getAsLong()) * 1000));
		
		// Temperature
		double tempKelvin = jsonObject.getAsJsonObject("main").get("temp").getAsDouble();
		int tempCelsius = (int) (tempKelvin - 273.15);

		// Humidity
		int humidity = jsonObject.getAsJsonObject("main").get("humidity").getAsInt();
		
		// Wind Speed
		double windSpeed = jsonObject.getAsJsonObject("wind").get("speed").getAsDouble();
		
		// Weather Condition
		String weatherCondition = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString();
		
		// Weather Icon
		String icon = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject().get("icon").getAsString();
		String iconId = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject().get("id").getAsString();
		
		// Set data as request attribute
		request.setAttribute("date", date);
		request.setAttribute("city", location.replace("%20", " "));
		request.setAttribute("temperature", tempCelsius);
		request.setAttribute("weatherCondition", weatherCondition);
		//request.setAttribute("weatherIcon", weatherDetails(icon));
		String weatherIcon = "";
		if(icon.contains("d")){
			weatherIcon = "wi-owm-day-" + iconId;
		} 
		else {
			weatherIcon = "wi-owm-night-" + iconId;
		}
		request.setAttribute("weatherIcon", weatherIcon);
		request.setAttribute("humidity", humidity);
		request.setAttribute("windSpeed", windSpeed);
		request.setAttribute("weatherData", responseContent.toString());
		
		connection.disconnect();
		
		// Send request to index.jsp page for rendering
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}
	
	protected String convertTime(long dt, int timeZone) {
		
		ZoneId zoneId = ZoneId.ofOffset("UTC", ZoneOffset.ofTotalSeconds(timeZone));
		
		// Convert Unix time stamp to ZonedDateTime
        ZonedDateTime zonedateTime = Instant.ofEpochSecond(dt).atZone(zoneId);
        
        // Format the date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, MMM dd, yyyy hh:mm a");
        String dateTime = zonedateTime.format(formatter);
		
		return dateTime;
	}

	protected String weatherDetails(String icon) {
		String weatherIcon = "";
		switch (icon) {
		case "01d":
			// Clear day sky
			weatherIcon = "wi-wu-clear";
			break;
			
		case "01n":
			// Clear night sky
			weatherIcon = "wi-night-clear";
			break;
			
		case "02d":
			// few clouds day sky
			weatherIcon = "wi-wu-partlycloudy";
			
			break;
			
		case "02n":
			// few clouds night sky
			weatherIcon = "wi-night-partly-cloudy";
			
			break;
			
		case "03n":
			// scattered clouds night
			weatherIcon = "wi-night-hazy";
			
			break;
			
		case "03d":
			// scattered clouds day
			weatherIcon = "wi-wu-partlycloudy";
			break;
			
		case "04d":
			// broken clouds day
			weatherIcon = " wi-wu-partlysunny";
			
			break;
			
		case "04n":
			// broken clouds nights
			weatherIcon = "wi-night-hazy";
			
			break;
			
		case "09d":
			// shower rain day
			weatherIcon = "wi-wu-rain";
			
			break;
			
		case "09n":
			// shower rain night
			weatherIcon = "wi-night-rain";
			
			break;

		case "10d":
			// rain day
			weatherIcon = "wi-wu-rain";
			
			break;
			
		case "10n":
			// rain night
			weatherIcon = "wi-night-rain";
			
			break;
			
		case "11d":
			// thunderstorm day
			weatherIcon = "wi-wu-tstorms";
			
			break;
			
		case "11n":
			// thunderstorm night
			weatherIcon = "wi-night-tstorms";
			break;

		case "13d":
			// snow day
			weatherIcon = "wi-wu-snow";
			
			break;
			
		case "13n":
			// snow night
			weatherIcon = "wi-night-snow";
			break;

		case "50d":
			// mist day
			weatherIcon = "wi-fog";
			break;
			
		case "50n":
			// mist night
			weatherIcon = "wi-night-fog";
			break;
			
		default:
			weatherIcon = "wi-wu-clear wi-wu-sunny";
			break;
		}
		return weatherIcon;
	}
}
