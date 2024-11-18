import React, { useState } from 'react';
import { NavItem, DropdownItem } from './types';
import { Link } from 'react-router-dom';

interface DropdownProps {
    navItem: NavItem;
    activeDropdown: number | null;
}

const Dropdown: React.FC<DropdownProps> = (props) => {
    const { navItem, activeDropdown } = props;
    return (
    <div>
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
    </div>);
}

export default Dropdown;

