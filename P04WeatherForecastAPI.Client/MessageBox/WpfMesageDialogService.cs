namespace P04WeatherForecastAPI.Client.MessageBox
{
    class WpfMesageDialogService : IMessageDialogService
    {
        public void ShowMessage(string message)
        {
             System.Windows.MessageBox.Show(message);
        }
    }
}
