using Dtos.Comment;
using Models;

namespace Mappers
{
    public static class CommentMappers
    {
        public static ReadCommentDto ToCommentDto(this Comment commentModel)
        {
            return new ReadCommentDto
            {
                Id = commentModel.Id,
                Title = commentModel.Title,
                Content = commentModel.Content,
                CreatedOn = commentModel.CreatedOn,
                StockId = commentModel.StockId
            };
        }

        public static Comment ToCommentFromCreate(this CreateCommentDto commentModel, int stockId)
        {
            return new Comment
            {
                Title = commentModel.Title,
                Content = commentModel.Content,
                StockId = stockId
            };
        }
    }
}