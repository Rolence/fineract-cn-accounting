/*
 * Copyright 2017 The Mifos Initiative.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.mifos.accounting.service.internal.service;

import io.mifos.accounting.api.v1.domain.JournalEntry;
import io.mifos.accounting.service.internal.mapper.JournalEntryMapper;
import io.mifos.accounting.service.internal.repository.JournalEntryEntity;
import io.mifos.accounting.service.internal.repository.JournalEntryRepository;
import io.mifos.core.lang.DateRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JournalEntryService {

  private final JournalEntryRepository journalEntryRepository;

  @Autowired
  public JournalEntryService(final JournalEntryRepository journalEntryRepository) {
    super();
    this.journalEntryRepository = journalEntryRepository;
  }

  public List<JournalEntry> fetchJournalEntries(final DateRange range) {
    final List<JournalEntryEntity> journalEntryEntities =
        this.journalEntryRepository.fetchJournalEntries(range);

    if (journalEntryEntities != null) {
      return journalEntryEntities
          .stream()
          .map(JournalEntryMapper::map)
          .collect(Collectors.toList());
    } else {
      return Collections.emptyList();
    }
  }

  public Optional<JournalEntry> findJournalEntry(final String transactionIdentifier) {
    final Optional<JournalEntryEntity> optionalJournalEntryEntity = this.journalEntryRepository.findJournalEntry(transactionIdentifier);

    return optionalJournalEntryEntity.map(JournalEntryMapper::map);
  }
}
