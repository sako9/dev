function love.load()
	love.graphics.setBackgroundColor(255,0,0)
end

function love.draw()
	local x = love.mouse.getX()
	local y = love.mouse.getY()
	love.graphics.setColor(x,y,x+y,255)
    love.graphics.rectangle("fill",x,y,50,50)
end