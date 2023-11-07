

using System.Collections.Generic;
using System.Threading.Tasks;
using P04WeatherForecastAPI.Client.Model.User;

namespace P04WeatherForecastAPI.Client.Services.UserServices
{
    public interface IUserService
    {
        Task<User> CreateUserAsync(User user);
        Task<bool> DeleteUserAsync(long id);
        // Task<User> GetUserByIdAsync(long userId);
        Task<List<User>> GetUsersAsync();
        Task<User> UpdateUserAsync(User user);

    }
}