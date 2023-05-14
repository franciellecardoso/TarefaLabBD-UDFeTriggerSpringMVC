CREATE DATABASE bdProduto
GO
USE bdProduto
GO
CREATE TABLE produto(
codigo			INT           NOT NULL,
nome			VARCHAR(100)  NOT NULL,    
valor_unitario	DECIMAL(7,2)  NOT NULL,
qtd_estoque		INT           NOT NULL
PRIMARY KEY(codigo)
)
GO
CREATE PROCEDURE sp_produto(@op CHAR(1), @codigo INT, @nome VARCHAR(100), 
        @valor_unitario DECIMAL(7,2), @qtd_estoque INT,
		@saida VARCHAR(MAX) OUTPUT)
AS
IF(UPPER(@op) = 'D' AND @codigo IS NOT NULL)
BEGIN
	DELETE produto WHERE codigo = @codigo
	SET @saida = 'Produto #ID ' + CAST(@codigo AS VARCHAR(5)) + ' excluido'
END
ELSE
BEGIN
	IF(UPPER(@op) = 'D' AND @codigo IS NULL)
	BEGIN
		RAISERROR('#ID invalido', 16, 1)
	END
	ELSE
	BEGIN
		IF(UPPER(@op) = 'I')
		BEGIN
			INSERT INTO produto VALUES
				(@codigo, @nome, @valor_unitario, @qtd_estoque)

			SET @saida = 'Produto #ID' + CAST(@codigo AS VARCHAR(5)) +
					' inserido com sucesso'
		END
		ELSE
		BEGIN
			IF(UPPER(@op) = 'U')
			BEGIN
				UPDATE produto
				SET nome = @nome, valor_unitario = @valor_unitario,
					qtd_estoque = @qtd_estoque
				WHERE codigo = @codigo

				SET @saida = 'Produto #ID ' + 
					CAST(@codigo AS VARCHAR(5))+
					' atualizado com sucesso' 
			END
			ELSE
			BEGIN
				RAISERROR('Codigo invalido', 16, 1)
			END
		END
	END
END
GO
DECLARE @saida VARCHAR(MAX)
EXEC sp_produto 'u', 1, 'Caderno', 5.99, 10, @saida OUTPUT 
PRINT @saida
GO
DECLARE @saida VARCHAR(MAX)
EXEC sp_produto 'i', 2, 'Caneta', 1.99, 8, @saida OUTPUT 
PRINT @saida
GO
DECLARE @saida VARCHAR(MAX)
EXEC sp_produto 'i', 3, 'Lapis', 0.99, 3, @saida OUTPUT 
PRINT @saida
GO
DECLARE @saida VARCHAR(MAX)
EXEC sp_produto 'i', 4, 'Borracha', 3.99, 0, @saida OUTPUT 
PRINT @saida

SELECT * FROM produto

--Scalar Function
GO
CREATE FUNCTION fn_prod(@codigo INT)
RETURNS DECIMAL(7,2)
AS
BEGIN
DECLARE @nome				VARCHAR(100),
		@valor_unitario		DECIMAL(7,2),
		@qtd_estoque		INT

SELECT @nome = nome, @valor_unitario = valor_unitario, @qtd_estoque = qtd_estoque FROM produto
WHERE codigo = @codigo
	
--SET @qtd_estoque
RETURN @qtd_estoque
END
GO
SELECT dbo.fn_prod(1) AS qtd_estoque
 
--Example 2 - Multi Statement Table
GO
CREATE FUNCTION fn_tabelaprod()
RETURNS @tabela TABLE(
codigo			INT,
nome			VARCHAR(100),
valor_unitario	DECIMAL(7,2),
qtd_estoque		INT,
condicao		VARCHAR(30)
)
AS
BEGIN
INSERT INTO @tabela(codigo, nome, valor_unitario, qtd_estoque)
SELECT codigo, nome, valor_unitario, qtd_estoque FROM produto
 
UPDATE @tabela SET qtd_estoque = (SELECT dbo.fn_prod(codigo))
 
	UPDATE @tabela SET condicao = 'SEM ESTOQUE'
	WHERE qtd_estoque = 0
	UPDATE @tabela SET condicao = 'ESTOQUE BAIXO'
	WHERE qtd_estoque > 1 AND qtd_estoque < 9
	UPDATE @tabela SET condicao = 'EM ESTOQUE'
	WHERE qtd_estoque >= 10

	RETURN 
END
GO 
SELECT * FROM fn_tabelaprod()

GO
CREATE TRIGGER t_produto_nao_exclui_estoque_disponivel ON produto
AFTER DELETE
AS
BEGIN
	DECLARE @qtd_estoque INT
	SET @qtd_estoque = (SELECT COUNT(*) FROM inserted)
	IF (@qtd_estoque > 0)
	BEGIN
		ROLLBACK TRANSACTION
		RAISERROR('Não é possível excluir produto com estoque!', 16, 1)
	END
END