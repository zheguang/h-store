/* This file is part of VoltDB.
 * Copyright (C) 2008-2010 VoltDB Inc.
 *
 * VoltDB is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * VoltDB is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with VoltDB.  If not, see <http://www.gnu.org/licenses/>.
 */

#ifndef ARRAYUNIQUEINDEX_H
#define ARRAYUNIQUEINDEX_H

#include "common/ids.h"
#include "common/tabletuple.h"
#include "indexes/tableindex.h"

#include <vector>
#include <string>
#include <map>

namespace voltdb {

class Table;
class TableTuple;

const int ARRAY_INDEX_INITIAL_SIZE = 131072; // (2^17)

/**
 * Unique Index specialized for 1 integer.
 * This is implemented as a giant array, which gives optimal performance as far
 * as the entry value is assured to be sequential and limited to a small number.
 * @see TableIndex
 */
class ArrayUniqueIndex : public LockBasedTableIndex {
    friend class TableIndexFactory;

    public:
        ~ArrayUniqueIndex();
        int64_t getMemoryEstimate() const {
            return sizeof(void*) * ARRAY_INDEX_INITIAL_SIZE;
        }
        std::string getTypeName() const { return "ArrayIntUniqueIndex"; };
    private:
        bool _addEntry(const TableTuple *tuples);
        bool _deleteEntry(const TableTuple *tuple);
        bool _replaceEntry(const TableTuple *oldTupleValue, const TableTuple* newTupleValue);
        bool _setEntryToNewAddress(const TableTuple *tuple, const void* address, const void* oldAddress);
        bool _exists(const TableTuple* values);
        bool _moveToKey(const TableTuple *searchKey);
        bool _moveToTuple(const TableTuple *searchTuple);
        TableTuple _nextValueAtKey();
        bool _advanceToNextKey();

        bool _checkForIndexChange(const TableTuple *lhs, const TableTuple *rhs);

        size_t _getSize() const { return (num_entries_); }

    protected:
        ArrayUniqueIndex(const TableIndexScheme &scheme);

        void **entries_;
        int32_t allocated_entries_;
        int32_t match_i_;
        int32_t num_entries_;
};

}

#endif // ARRAYUNIQUEINDEX_H
