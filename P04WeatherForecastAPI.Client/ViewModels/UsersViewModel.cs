using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using P04WeatherForecastAPI.Client.MessageBox;
using P04WeatherForecastAPI.Client.Model.User;
using P04WeatherForecastAPI.Client.Services.UserServices;
using System.Collections.ObjectModel;
using System.Threading.Tasks;

namespace P04WeatherForecastAPI.Client.ViewModels
{
    public partial class UsersViewModel : ObservableObject
    {
        private readonly IUserService _userService;

        private readonly IMessageDialogService _messageDialogService;

        private readonly UserDetailsView _userDetailsView;

        public ObservableCollection<User> Users { get; set; }

        [ObservableProperty]
        private User selectedUser;

        public UsersViewModel(IUserService userService, UserDetailsView userDetailsView, IMessageDialogService messageDialogService)
        {
            _messageDialogService = messageDialogService;
            _userDetailsView = userDetailsView;
            _userService = userService;
            Users = new ObservableCollection<User>();          
        }

        public async Task GetUsers()
        {
            Users.Clear();
            var usersResult = await _userService.GetUsersAsync();
            if (usersResult != null)
            {
                foreach (User u in usersResult)
                {
                    Users.Add(u);
                }
            }
        }

         public async Task CreateUser()
        {
            var newUser = new User()
            {
                Name = selectedUser.Name,
                Surname = selectedUser.Surname,
                Email = selectedUser.Email,
                Password = "przyimplementacjirejestracji"
            };

            var result =  await _userService.CreateUserAsync(newUser);

            await GetUsers();
            if (result.Name != null)
                await GetUsers();
            else
                _messageDialogService.ShowMessage("SIEMA COS NIE TAK KOLEGO");  
                // _messageDialogService.ShowMessage(result.Message);  
        }

        public async Task UpdateUser()
        {
            var userToUpdate = new User()
            {
                Id = selectedUser.Id,
                Name = selectedUser.Name,
                Surname = selectedUser.Surname,
                Email = selectedUser.Email,
            };

            await _userService.UpdateUserAsync(userToUpdate);
            GetUsers();
        }
         public async Task DeleteUser()
        {
            await _userService.DeleteUserAsync(selectedUser.Id);
            await GetUsers();
        }

        [RelayCommand]
        public async Task ShowDetails(User user)
        {
            _userDetailsView.Show();
            _userDetailsView.DataContext = this;
            //selectedProduct = product;
            //OnPropertyChanged("SelectedProduct");
            SelectedUser = user;
        }

        
        [RelayCommand]
        public async Task Save()
        {
            if (selectedUser.Id == 0)
            {
                CreateUser();
            }
            else
            {
                UpdateUser();
            }

        }

        [RelayCommand]
        public async Task Delete()
        {
            DeleteUser();
        }

        [RelayCommand]
        public async Task New()
        {
            _userDetailsView.Show();
            _userDetailsView.DataContext = this;
            //selectedProduct = new Product();
            //OnPropertyChanged("SelectedProduct");
            SelectedUser = new User(); 
        }

    }
}
