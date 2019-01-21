using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace Schoopy
{
    /// <summary>
    /// Interaction logic for LessonDetail.xaml
    /// </summary>
    public partial class LessonDetail : Window
    {
        Lesson initLesson;
        public LessonDetail(Lesson l)
        {
            InitializeComponent();
            initLesson = l;
            drawSchool();
        }

        public void init()
        {
            labelHour.Content = initLesson.schoolHour.ToString();
            labelRomm.Content = initLesson.teachingRoom.roomNr.ToString();
            labelWeekday.Content = initLesson.weekday.ToString();


        }

        public void drawSchool()
        {
            Line line = new Line();
            line.Stroke = Brushes.LightSteelBlue;

            line.X1 = 0;
            line.X2 = 22 * 20;
            line.Y1 = 0;
            line.Y2 = 0;

            line.StrokeThickness = 2;
            lessonCanvas.Children.Add(line);

            Line line2 = new Line();
            line2.Stroke = Brushes.LightSteelBlue;
            line2.X1 = 0;
            line2.X2 = 0;
            line2.Y1 = 0;
            line2.Y2 = 14*20;
            line2.StrokeThickness = 2;
            lessonCanvas.Children.Add(line2);

            Line line3 = new Line();
            line3.Stroke = Brushes.LightSteelBlue;
            line3.X1 = 0;
            line3.X2 = 22*20;
            line3.Y1 = 14*20;
            line3.Y2 = 14 * 20;
            line3.StrokeThickness = 2;
            lessonCanvas.Children.Add(line3);

            Line line4 = new Line();
            line4.Stroke = Brushes.LightSteelBlue;
            line4.X1 = 22*20;
            line4.X2 = 22*20;
            line4.Y1 = 14 * 20;
            line4.Y2 = 0;
            line4.StrokeThickness = 2;
            lessonCanvas.Children.Add(line4);

            Line line5 = new Line();
            line5.Stroke = Brushes.LightSteelBlue;
            line5.X1 = 4.5 * 20;
            line5.X2 = 4.5 * 20;
            line5.Y1 = 0;
            line5.Y2 = 14*20;
            line5.StrokeThickness = 2;
            lessonCanvas.Children.Add(line5);

            Line line6 = new Line();
            line6.Stroke = Brushes.LightSteelBlue;
            line6.X1 = 0;
            line6.X2 = 4.5 * 20;
            line6.Y1 = 4*20;
            line6.Y2 = 4* 20;
            line6.StrokeThickness = 2;
            lessonCanvas.Children.Add(line6);

            Line line7 = new Line();
            line7.Stroke = Brushes.LightSteelBlue;
            line7.X1 = 0;
            line7.X2 = 4.5 * 20;
            line7.Y1 = 8 * 20;
            line7.Y2 = 8 * 20;
            line7.StrokeThickness = 2;
            lessonCanvas.Children.Add(line7);

            Line line8 = new Line();
            line8.Stroke = Brushes.LightSteelBlue;
            line8.X1 = 4.5*20;
            line8.X2 = 22 * 20;
            line8.Y1 = 9 * 20;
            line8.Y2 = 9 * 20;
            line8.StrokeThickness = 2;
            lessonCanvas.Children.Add(line8);


            Line line9 = new Line();
            line9.Stroke = Brushes.LightSteelBlue;
            line9.X1 = 9 * 20;
            line9.X2 = 9 * 20;
            line9.Y1 = 9 * 20;
            line9.Y2 = 14 * 20;
            line9.StrokeThickness = 2;
            lessonCanvas.Children.Add(line9);

            Line line10 = new Line();
            line10.Stroke = Brushes.LightSteelBlue;
            line10.X1 = 15 * 20;
            line10.X2 = 15 * 20;
            line10.Y1 = 9 * 20;
            line10.Y2 = 14 * 20;
            line10.StrokeThickness = 2;
            lessonCanvas.Children.Add(line10);

            Line line11 = new Line();
            line11.Stroke = Brushes.LightSteelBlue;
            line11.X1 = 18.5 * 20;
            line11.X2 = 18.5 * 20;
            line11.Y1 = 0;
            line11.Y2 = 9 * 20;
            line11.StrokeThickness = 2;
            lessonCanvas.Children.Add(line11);

            Line line12 = new Line();
            line12.Stroke = Brushes.LightSteelBlue;
            line12.X1 = 18.5 * 20;
            line12.X2 = 22 * 20;
            line12.Y1 = 5 * 20;
            line12.Y2 = 5 * 20;
            line12.StrokeThickness = 2;
            lessonCanvas.Children.Add(line12);

           
            

        }
    }
}
