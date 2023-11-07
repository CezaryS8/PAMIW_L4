using CommunityToolkit.Mvvm.ComponentModel;
using P04WeatherForecastAPI.Client.Model.User;
using P04WeatherForecastAPI.Client.Services.UserServices;
using System.Collections.ObjectModel;

namespace P04WeatherForecastAPI.Client.ViewModels
{
    public partial class UsersViewModel : ObservableObject
    {
        private readonly IUserService _userService;

        public ObservableCollection<User> Users { get; set; }

        public UsersViewModel(IUserService userService)
        {
            _userService = userService;
            Users = new ObservableCollection<User>();          
        }

        public async void GetUsers()
        {
            Users.Clear();
            var usersResult = await _userService.GetUsers();
            if (usersResult != null)
            {
                foreach (User u in usersResult)
                {
                    Users.Add(u);
                }
            }
        }

    }
}
