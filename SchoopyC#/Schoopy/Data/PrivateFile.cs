using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Schoopy.Data
{
    class PrivateFile : PublicFile
    {
        public Student publisherStudent { get; set; }
        public Room folderRoom { get; set; }

        public PrivateFile(int _fileId, String _filename, byte[] _fileContent, LocalDate _publishDate, Teacher _publisherTeacher, Student _publisherStudent, Room _folderRoom)
        {
            this.fileId = _fileId;
            this.fileName = _filename;
            this.fileContent = _fileContent;
            this.publishDate = _publishDate;
            this.publisherTeacher = _publisherTeacher;
            this.publisherStudent = _publisherStudent;
            this.folderRoom = _folderRoom;

        }
        public PrivateFile()
        {

        }
    }
}
