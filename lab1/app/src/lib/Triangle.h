#ifndef TRIANGLE_H
#define TRIANGLE_H

#include <algorithm>
#include <cstdlib>
#include <iterator>

constexpr uint TRIANGLE_SIDES_AMOUNT = 3;

class Triangle
{
public:
	enum class Type
	{
		DEFAULT,
		ISOSCELES,
		EQUILATERAL,
		NOT_A_TRIANGLE,
	};

	Triangle(uint sideA, uint sideB, uint sideC)
		: m_sides{sideA, sideB, sideC}
	{
	}

	Type GetType()
	{
		for (auto side : m_sides)
		{
			if (side == 0)
			{
				return Type::NOT_A_TRIANGLE;
			}
		}

		switch (CountEqualSides())
		{
		case 3:
			return Type::EQUILATERAL;
		case 2:
			return Type::ISOSCELES;
		default:
			return Type::DEFAULT;
		}
	}

private:
	uint m_sides[TRIANGLE_SIDES_AMOUNT];

	uint CountEqualSides()
	{
		uint result = 0;
		for (auto side : m_sides)
		{
			auto amountOfEqualSides = std::count(std::begin(m_sides), std::end(m_sides), side);
			result = std::max<uint>(amountOfEqualSides, result);
		}
		return result;
	}
};

#endif // TRIANGLE_H
