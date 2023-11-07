using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Options;
using Microsoft.VisualBasic.FileIO;
using Newtonsoft.Json;
using P04WeatherForecastAPI.Client.Configuration;
using P04WeatherForecastAPI.Client.Model.User;
using P06Shop.Shared;
using P06Shop.Shared.Shop;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net.Http;
using System.Net.Http.Json;
using System.Text;
using System.Threading.Tasks;
using System.Windows;

namespace P04WeatherForecastAPI.Client.Services.UserServices
{
    internal class UserService : IUserService
    {
        private readonly HttpClient _httpClient;
        private readonly AppSettings _appSettings;
        public UserService(HttpClient httpClient, IOptions<AppSettings> appSettings)
        {
            _httpClient= httpClient;
            _appSettings= appSettings.Value;
        }

        public async Task<User> CreateUserAsync(User user)
        {
            string requestUrl = _appSettings.BaseSpringAPIUrl + _appSettings.BaseUserEndpoint.Base_url + _appSettings.BaseUserEndpoint.GetAllUsersEndpoint;
            var response = await _httpClient.PostAsJsonAsync(requestUrl, user);
            var result = await response.Content.ReadFromJsonAsync<User>();
            return result;
        }

        public async Task<bool> DeleteUserAsync(long id)
        {
            // jesli uzyjemy / na poczatku to wtedy sciezka trakktowana jest od root czyli pomija czesc środkową adresu 
            // zazwyczaj unikamy stosowania / na poczatku 
            string requestUrl = _appSettings.BaseSpringAPIUrl + _appSettings.BaseUserEndpoint.Base_url + _appSettings.BaseUserEndpoint.GetAllUsersEndpoint+id;
            var response = await _httpClient.DeleteAsync(requestUrl);
            var result = await response.Content.ReadFromJsonAsync<bool>();
            return result;
        }

        public async Task<List<User>> GetUsersAsync()
        {   
            string requestUrl = _appSettings.BaseSpringAPIUrl + _appSettings.BaseUserEndpoint.Base_url + _appSettings.BaseUserEndpoint.GetAllUsersEndpoint;
            Console.WriteLine(requestUrl);
            var response = await _httpClient.GetAsync(requestUrl);
            var json = await response.Content.ReadAsStringAsync();
            var result = JsonConvert.DeserializeObject<List<User>>(json);
            return result;
        }

        public async Task<User> UpdateUserAsync(User user)
        {
            string requestUrl = _appSettings.BaseSpringAPIUrl + _appSettings.BaseUserEndpoint.Base_url + _appSettings.BaseUserEndpoint.GetAllUsersEndpoint;
            var response = await _httpClient.PutAsJsonAsync(requestUrl, user);
            var result = await response.Content.ReadFromJsonAsync<User>();
            return result;
        }

        // public async Task<User> GetUserByIdAsync(long userId)
        // {
        //     var response = await _httpClient.GetAsync("http://localhost:8080/api/v1/user/1");
        //     var json = await response.Content.ReadAsStringAsync();
        //     var result = JsonConvert.DeserializeObject<User>(json);
        //     Console.WriteLine(result);
        //     return result;
        // }
    }
}
