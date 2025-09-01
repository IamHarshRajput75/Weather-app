const API_BASE_URL = 'http://localhost:8082/weather/forecast';

// DOM Elements
const searchSection = document.getElementById('searchForm');
const resultsSection = document.getElementById('resultsSection');
const errorSection = document.getElementById('errorSection');
const searchBtn = document.getElementById('searchBtn');
const btnText = document.getElementById('btnText');
const loader = document.getElementById('loader');

// Helper Functions
function showLoader() {
  btnText.classList.add('hidden');
  loader.classList.remove('hidden');
  searchBtn.disabled = true;
}

function hideLoader() {
  btnText.classList.remove('hidden');
  loader.classList.add('hidden');
  searchBtn.disabled = false;
}

function showSection(section) {
  // Hide all sections
  resultsSection.classList.add('hidden');
  errorSection.classList.add('hidden');
  
  // Show requested section
  section.classList.remove('hidden');
}

function formatDate(dateString) {
  const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
  return new Date(dateString).toLocaleDateString('en-US', options);
}

function renderCurrentWeather(data) {
  document.getElementById('city').textContent = data.weatherResponse.city;
  document.getElementById('region').textContent = data.weatherResponse.region;
  document.getElementById('country').textContent = data.weatherResponse.country;
  document.getElementById('temperature').textContent = data.weatherResponse.temperature;
  document.getElementById('condition').textContent = data.weatherResponse.condition;
}

function renderForecast(data) {
  const container = document.getElementById('forecastCards');
  container.innerHTML = '';
  
  data.dayTem.forEach(day => {
    const card = document.createElement('div');
    card.className = 'card';
    card.innerHTML = `
      <div class="card-header">${formatDate(day.date)}</div>
      <div class="card-body">
        <div class="temp-item">
          <p>${day.minTemp}°C</p>
          <p>Min</p>
        </div>
        <div class="temp-item">
          <p>${day.maxTemp}°C</p>
          <p>Max</p>
        </div>
        <div class="temp-item">
          <p>${day.avgTemp}°C</p>
          <p>Avg</p>
        </div>
      </div>
    `;
    container.appendChild(card);
  });
}

async function fetchWeatherData(city, days) {
  try {
    showLoader();
    
    const url = `${API_BASE_URL}?city=${encodeURIComponent(city)}&days=${days}`;
    const response = await fetch(url);
    
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    
    const data = await response.json();
    
    // Render the results
    renderCurrentWeather(data);
    renderForecast(data);
    showSection(resultsSection);
    
  } catch (error) {
    console.error('Error fetching weather data:', error);
    document.getElementById('errorMessage').textContent = 
      `Unable to fetch weather data for "${city}". Please check the city name and try again.`;
    showSection(errorSection);
  } finally {
    hideLoader();
  }
}

// Event Listeners
document.getElementById('searchForm').addEventListener('submit', function(e) {
  e.preventDefault();
  
  const city = document.getElementById('cityInput').value.trim();
  const days = parseInt(document.getElementById('daysInput').value) || 3;
  
  if (!city) {
    alert('Please enter a city name');
    return;
  }
  
  if (days < 1 || days > 7) {
    alert('Days must be between 1 and 7');
    return;
  }
  
  fetchWeatherData(city, days);
});

document.getElementById('searchAgainBtn').addEventListener('click', function() {
  // Clear form
  document.getElementById('cityInput').value = '';
  document.getElementById('daysInput').value = '3';
  
  // Hide results and show search section
  showSection(document.createElement('div')); // This hides all sections
  
  // Focus on city input
  document.getElementById('cityInput').focus();
});

document.getElementById('retryBtn').addEventListener('click', function() {
  const city = document.getElementById('cityInput').value.trim();
  const days = parseInt(document.getElementById('daysInput').value) || 3;
  
  if (city) {
    fetchWeatherData(city, days);
  } else {
    showSection(document.createElement('div')); // Hide all sections to show search
  }
});

// Initialize the app
window.onload = function() {
  document.getElementById('cityInput').focus();
};
