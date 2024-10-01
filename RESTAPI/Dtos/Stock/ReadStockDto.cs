using System.ComponentModel.DataAnnotations;
using Dtos.Comment;
namespace Dtos.Stock
{
    public class ReadStockDto
    {
        [Required]
        public int Id { get; set; }
        [Required]
        public string Symbol { get; set; } = string.Empty;
        [Required]
        public string CompanyName { get; set; } = string.Empty;
        [Required]
        public decimal Purchase { get; set; }
        [Required]
        public decimal LastDiv { get; set; }
        [Required]
        public string Industry { get; set; } = string.Empty;
        [Required]
        public long MarketCap { get; set; }
        public List<ReadCommentDto> Comments { get; set; } = new List<ReadCommentDto>();
    }
}