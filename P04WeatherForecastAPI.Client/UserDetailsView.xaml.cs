using System.Windows;

namespace P04WeatherForecastAPI.Client
{
    /// <summary>
    /// Interaction logic for UserDetailsView.xaml
    /// </summary>
    public partial class UserDetailsView : Window
    {
        public UserDetailsView()
        {
            InitializeComponent();
        }

        private void Window_Closing(object sender, System.ComponentModel.CancelEventArgs e)
        {
            e.Cancel = true;
            this.Hide();
        }
    }
}
