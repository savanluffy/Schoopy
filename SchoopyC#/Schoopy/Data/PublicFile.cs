using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Schoopy.Data
{
    public class PublicFile
    {
        public int fileId { get; set; }
        public String fileName { get; set; }
        public byte[] fileContent { get; set; }
        public LocalDate publishDate { get; set; }
        public Teacher publisherTeacher { get; set; }

        public PublicFile(int _fileId, String _filename, byte[] _fileContent, LocalDate _publishDate, Teacher _publisherTeacher)
        {
            this.fileId = _fileId;
            this.fileName = _filename;
            this.fileContent = _fileContent;
            this.publishDate = _publishDate;
            this.publisherTeacher = _publisherTeacher;
            
        }
        public PublicFile()
        {


        }

        public override string ToString()
        {
            return this.fileName;
        }

    }
}
