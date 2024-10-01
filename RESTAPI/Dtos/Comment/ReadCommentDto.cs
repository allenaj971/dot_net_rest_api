using System;
using System.ComponentModel.DataAnnotations;

namespace Dtos.Comment
{
    public class ReadCommentDto
    {
        public int Id { get; set; }
        [Required]
        [MaxLength(280, ErrorMessage = "Title character limit of 280")]
        public string Title { get; set; } = string.Empty;
        [Required]
        [MaxLength(1000, ErrorMessage = "Title character limit of 1000")]
        public string Content { get; set; } = string.Empty;
        [Required]
        public DateTime CreatedOn { get; set; } = DateTime.Now;
        //navigation property allows us to navigate within stock and comment relationship
        [Required]
        public int? StockId { get; set; }
    }
}