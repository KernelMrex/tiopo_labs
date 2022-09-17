#include "lib/Triangle.h"
#include <climits>
#include <cstdlib>
#include <iostream>

constexpr uint MAX_ALLOWED_INPUT = UINT_MAX / 2 - 1;

inline bool isNumber(const std::string& s)
{
	return s.find_first_not_of("0123456789") == std::string::npos;
}

inline uint stoui(const std::string& s)
{
	if (!isNumber(s))
	{
		throw std::invalid_argument("value must be numeric");
	}
	auto val = std::stoul(s, nullptr, 10);
	if (val > MAX_ALLOWED_INPUT)
	{
		throw std::out_of_range("value is out of range");
	}
	return val;
}

int main(int argc, char* argv[])
{
	if (argc != 4)
	{
		std::cerr << "error" << std::endl;
		return EXIT_FAILURE;
	}

	try
	{
		Triangle triangle(
			stoui(argv[1]),
			stoui(argv[2]),
			stoui(argv[3]));

		switch (triangle.GetType())
		{
		case Triangle::Type::EQUILATERAL:
			std::cout << "equilateral" << std::endl;
			break;
		case Triangle::Type::ISOSCELES:
			std::cout << "isosceles" << std::endl;
			break;
		case Triangle::Type::DEFAULT:
			std::cout << "default" << std::endl;
			break;
		case Triangle::Type::NOT_A_TRIANGLE:
			std::cout << "not a triangle" << std::endl;
			break;
		}
	}
	catch (const std::invalid_argument&)
	{
		std::cerr << "error" << std::endl;
		return EXIT_FAILURE;
	}
	catch (const std::out_of_range&)
	{
		std::cerr << "error" << std::endl;
		return EXIT_FAILURE;
	}

	return EXIT_SUCCESS;
}