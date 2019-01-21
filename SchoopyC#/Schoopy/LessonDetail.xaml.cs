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
        }

        public void init()
        {
            labelHour.Content = initLesson.schoolHour.ToString();
            labelRomm.Content = initLesson.teachingRoom.roomNr.ToString();
            labelWeekday.Content = initLesson.weekday.ToString();

        }
    }
}
