

using System.Collections.Generic;
using System.Threading.Tasks;
using P04WeatherForecastAPI.Client.Model.User;

namespace P04WeatherForecastAPI.Client.Services.UserServices
{
    public interface IUserService
    {
        Task<User> GetUserByIdAsync(long userId);
        Task<List<User>> GetUsers();
    }
}