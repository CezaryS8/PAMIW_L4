using P04WeatherForecastAPI.Client.ViewModels;
using System.Windows;

namespace P04WeatherForecastAPI.Client
{
    /// <summary>
    /// Interaction logic for ShopProductsWindow.xaml
    /// </summary>
    public partial class UsersView : Window
    {
        public UsersView(UsersViewModel usersViewModel)
        {
            DataContext = usersViewModel;
            InitializeComponent();
        }
    }
}
