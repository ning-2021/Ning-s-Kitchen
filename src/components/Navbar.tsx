import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { NavItem, DropdownItem } from './types';
import dotenv from 'dotenv';
import axios from 'axios';

const Navbar: React.FC = () => {
    const [navItems, setNavItems] = useState<NavItem[]>([]);
    const [error, setError] = useState<string | null>(null);
    const [activeDropdown, setActiveDropdown] = useState<number | null>(null);

    const fetchNavItems = async() => {
        try {
            const response = await axios.get<NavItem[]>('/api/navbarItems');
            // transform the API data to match the NavItem interface
            const transformedNavItems: NavItem[] = response.data.map(item => {
                const parentHref = `/${item.name.toLowerCase().replace(/\s+/g, '-')}`;
                return {
                    id: item.id,
                    name: item.name,
                    href: parentHref,
                    dropdownItems: item.dropdownItems?.map(dropdownItem => ({
                        id: dropdownItem.id,
                        name: dropdownItem.name,
                        href: `${parentHref}/${dropdownItem.name.toLowerCase().replace(/\s+/g, '-')}`
                    })) ?? []
                };
            });
            setNavItems(transformedNavItems);
        } catch (err) {
            setError('Failed to fetch navigation data');
        }
    }

    useEffect(() => {
        fetchNavItems();
    }, []);

    const handleMouseEnter = (navItemId: number): void => {
        setActiveDropdown(navItemId);
        console.log(navItemId);
    };

    const handleMouseLeave = (): void => {
        setActiveDropdown(null);
    }

    return (
        <nav className="navbar">
            <ul className="navbar-links">
                <Link to={"/"}>Home</Link>
                {navItems.map(navItem => (
                    <li key={navItem.id} onMouseEnter={() => handleMouseEnter(navItem.id)} onMouseLeave={() => handleMouseLeave()}>
                        <Link to={navItem.href}>{navItem.name}</Link>
                        {activeDropdown === navItem.id && navItem.dropdownItems && navItem.dropdownItems.length > 0 && (
                            <ul className="dropdown">
                                {navItem.dropdownItems.map(dropdownItem => (
                                    <li key={dropdownItem.id}>
                                        <Link to={dropdownItem.href}>{dropdownItem.name}</Link>
                                    </li>
                                ))}
                            </ul>
                        )}
                    </li>
                ))}
            </ul>
        </nav>
    );
};

export default Navbar;
