using System;
using System.ComponentModel.DataAnnotations;

namespace Dtos.Comment

{
    public class CreateCommentDto
    {
        [Required]
        [MaxLength(280, ErrorMessage = "Title character limit of 280")]
        public string Title { get; set; } = string.Empty;
        [Required]
        [MaxLength(1000, ErrorMessage = "Title character limit of 1000")]
        public string Content { get; set; } = string.Empty;
    }
}